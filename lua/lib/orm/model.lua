--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 09/04/17
-- Time: 23:00
-- To change this template use File | Settings | File Templates.
--

-- 
-- Criar uma tabela de colunas, para mapear isto. após isto, estes nomes de variáveis serão usadas apenas como gets e sets associando às colunas.
-- ex: leonardo = Pessoa({nome='leonardo'}) == o nome da classe e do arquivo devem ser exatamente iguais
-- se existir a coluna, https://www.lua.org/pil/13.4.4.html
--[[ Etapas de criação:
-- 1. Definição da estrutura do Model
--- local Pessoa = Model:extend("Pessoa", 
          { 
            columns = {
                nome = {},
                idade = { notNull=true }
          }
      })
-- 2. Criação de uma instância
--- local leonardo = Pessoa({nome="Leonardo", idade=30})
-- 3. Salvar:
--- leonardo:save()
  ]]

-- TODO: não permitir duas foreignkeys para um relacionamento

local class

--[[
-- exemplo:
 - local Casa = Model:extend("Casa", { columns = {
    nome = {},
    idade = { notNull=true }
  },
  hasOne = {
    endereco = { class }
  }
  })
 ]]

TEST = false
local class
if TEST == false then
    class = require("lib.external.30log")
    require("lib.orm.create_table")
    require("lib.util.table_util")
else
    class = require("30log")
    require("create_table")
    DBAdapter = { -- mock class
        querySQL = function(query)
            return {}
        end,
        executeSQL = function(query)
            return true
        end
    }
end

--[[
-- Represents a single row on database following Active Record pattern.
 - Contains methods to insert and query table.
 -
 ]]
Model = class("Model")

function Model:init(columns, model)

    if columns == nil or type(columns) ~= "table" then
        error("A model cannot be instantiated without column")
    end
    
    if model == nil or type(model) ~= "table" then
        error("Did you missed to pass your model?!")
    end
    
    if model.columns == nil or type(model.columns) ~= "table" then
        error("Did you missed to create your model columns definitions?")
    end
    -- percorrer a tabela
    
    -- save columns values in model.
    for columnName, value in pairs(columns) do
        if columnName ~= nil then              
              model[columnName] = value
        end
    end
    
    self:_createTableSQL(model)
end

function Model:findAll()
    local generatedSql = Table:_generateSQLSelect(self)
    -- reset clauses, so it can be set next time.
    self.whereClause = nil
    self.orClause = nil
    local rawResults = DBAdapter:querySQL(generatedSql)
    local models = self:buildModels(rawResults)

    return models
end

function Model:where(conditions)
    self.whereClause = Table:_generateSQLWhere(conditions)
    return self
end

function Model:OR(conditions)
    if self.whereClause == nil or type(self.whereClause) ~= "string" then
        error("You must call WHERE before calling or.")
    end

    self.orClause = Table:_generateSQLWhereOR(conditions)
    return self
end

function Model:buildModels( rawNativeSQLResult )
    if rawNativeSQLResult == nil then
        error("Cannot generate models without SQL rows.")
    end


    if ((type(rawNativeSQLResult) ~= "table" and type(rawNativeSQLResult) ~= "userdata") or rawNativeSQLResult["count"] == nil or rawNativeSQLResult["get"] == nil) then
        error("Cannot generate models without a valid raw sql.")
    end

    local models = {}

    for i=0, rawNativeSQLResult:count()-1 do -- starts in 0 as it is a Java/Objective-c array

        local columnValueDictionary = rawNativeSQLResult:get(i) -- está nil

        local model = {}
        local belongedModels = {}
        local hasManyModels = {}

        if self.belongsTo ~= nil then
            for foreignKey, foreignTable in pairs(self.belongsTo) do

                belongedModels[foreignKey] = {}
            end
        end

        if self.hasMany ~= nil and type(self.hasMany) == "table" then
            for foreignKey, foreignTable in pairs(self.hasMany) do

                hasManyModels[foreignKey] = {}
            end
        end

        local columns = columnValueDictionary:getAllKeys()

        for j=0, columns:count()-1 do -- starts in 0 as it is a Java/Objective-c array

            local columnNameWithAlias = columns:get(j)
            local columnNameWithoutAlias = string.gsub(columnNameWithAlias, "^(.*)_", "")
            local alias = string.gsub(columnNameWithAlias, "_(.*)", "")
            local columnValue = columnValueDictionary:get(columnNameWithAlias)

            if alias == self.name then
                model[columnNameWithoutAlias] = columnValue
            else
                if self.belongsTo ~= nil and self.belongsTo[alias] ~= nil then
                    belongedModels[alias][columnNameWithoutAlias] = columnValue
                end
            end

        end

        for foreignKeyName, v in pairs(belongedModels) do

            if self.belongsTo ~= nil and self.belongsTo[foreignKeyName] ~= nil then

                local belongedClass = require("models."..self.belongsTo[foreignKeyName])
                local belongedModel = belongedClass(belongedModels[foreignKeyName])

                model[foreignKeyName] = belongedModel

            end
        end

        if model.rowid ~= nil then
            for recordsName, className in pairs(hasManyModels) do
                local foreignKey = self.name.."_id"
                local condition = {}
                condition[foreignKey] = model.rowid
                local hasManyClass = require("models."..self.hasMany[recordsName])
                -- generate tables, if not already.
-- TODO: must change it to use a startup script which does this.
-- TODO: list all models and call create table on it.
-- TODO: lua does not have this native. :(
                hasManyClass:_createTableSQL(hasManyClass)
                local foreignRows = hasManyClass:where(condition):findAll()
                if foreignRows ~= nil and type(foreignRows) == "table" and #foreignRows > 0 then
                    model[recordsName] = foreignRows
                else
                    model[recordsName] = {}
                end
            end
        end


        local myClass = require("models."..self.name)
        local myModel = myClass(model)

        table.insert(models, myModel)
    end

    return models
end

function Model:delete()
    local generatedSql = Table:_generateSQLDelete(self)
    local result = DBAdapter:executeSQL(generatedSql)
    if result then
        self.rowid = nil
        return true
    end

    return false
end

function Model:save()
    if self.rowid == nil then

        local generatedSqlSave = Table:_generateSQLInsert(self)
        local primaryKey = DBAdapter:save(generatedSqlSave)

        if primaryKey ~= nil then
            self.rowid = primaryKey
            return true
        end

        return false
    else
        local generatedSql = Table:_generateSQLUpdate(self)
        local result = DBAdapter:executeSQL(generatedSql)
        if result then
            return true
        end

        return false
    end
end

-- Creates a table in SQLite based on this model columns definitions
function Model:_createTableSQL(model)
    local result
    if model.isTableCreated == nil or model.isTableCreated == false then

      if model.columns == nil and model.name == nil then
          error( "You should not call this method directly.")
      end

      local sql = Table:_generateSQL(model)

      if TEST == false then
        result = DBAdapter:executeSQL(sql)
        -- should not create a table everytime a new instance is created
        local myClass = require("models."..model.name)
        myClass.isTableCreated = true
      else
        result = true
      end


    end

    return result
end
return Model
