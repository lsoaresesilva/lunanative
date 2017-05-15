--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 09/04/17
-- Time: 22:48
-- To change this template use File | Settings | File Templates.
--

--[[
-- TODO:
 - * Gerar SQL CREATE TABLE

  * Gerar SQL Insert
  * Gerar SQL Select
  * Gerar SQL Where
  * Gerar SQL order
  * Gerar SQL group
 ]]
DEBUG = false
local class
if DEBUG == false then
    class = require("lib.external.30log")
    require("lib.util.table_util")
else
    class = require("30log")
    require("table_util")
end

--[[
-- Generates SQLs queries as strings to be used on SQLite
 ]]
Table = class("Table")


function Table:_isModelValid(model)
    if model == nil or type(model) ~= "table" then
        error("A model was expected to be different from nil and as a lua table.")
    end

    if model.name == nil or model.columns == nil or type(model.columns) ~= "table" then
        error("Your model must have a name and a columns as properties.")
    end

    return true
end

function Table:_extractValuesForColumnsFromModel(model)
    local columnValues = {}
    for columnName, definitions in pairs(model.columns) do
        local columnValue = "null"
        if model[columnName] ~= nil then
            if type(model[columnName]) == "string" then
                columnValue = "'"..model[columnName].."'"
            else
                columnValue = model[columnName]
            end
        end

        --table.insert(columnValues, columnValue)
        columnValues[columnName] = columnValue

    end

    return columnValues
end

-- TODO: verificar para que o usuário não coloque nomes inválidos para a coluna (http://www.sqlite.org/lang_keywords.html)
function Table:_generateSQL(model)
  local sql
  if self:_isModelValid(model) then

      local sizeOfColumns = table.getSize(model.columns)
      local counterForLoop = sizeOfColumns
      --[[for columnName, definitions in pairs(model.columns) do
              numberOfColumns = numberOfColumns+1
          end]]

      sql = "CREATE TABLE IF NOT EXISTS "..model.name.." ("
      for columnName, definitions in pairs(model.columns) do
              local columnDefinitionSQL = ""
              for k,v in pairs(definitions) do
                  if k == "notNull" and v == true then
                      columnDefinitionSQL = columnDefinitionSQL.."not null"
                  end
              end
              sql = sql.." "..columnName.." "..columnDefinitionSQL..""
              counterForLoop = counterForLoop-1
              if counterForLoop == 0 then
                --sql = sql..")"
              else
                sql = sql..","
              end
      end
      local sqlForeignKeys = ""
      if model.belongsTo ~= nil and type(model.belongsTo) == "table" then
          local sizeOfBelongsTo = table.getSize(model.belongsTo)
          local counterForLoop = sizeOfBelongsTo

          for foreignKey, foreignTable in pairs(model.belongsTo) do
                sql = sql..", "..foreignKey.."_id"
                sqlForeignKeys = sqlForeignKeys..", ".."FOREIGN KEY("..foreignKey.."_id) REFERENCES "..foreignTable.."(rowid)"
                counterForLoop = counterForLoop-1
                if counterForLoop == 0 then
                    --sql = sql..")"
                else
                    --sql = sql..","
                end
          end
          sql = sql..sqlForeignKeys

      --else


      end
      sql = sql..")"

      --FOREIGN KEY(chave_primaria) REFERENCES tabela_externa(id_externo)
  end

  return sql
end

function Table:_generateSQLInsert(model)
    local sql = nil
    if self:_isModelValid(model) then

        --local columnValues = {}
        local columnValues = self:_extractValuesForColumnsFromModel(model)

        local sizeOfColumns = table.getSize(model.columns)
        local counterForLoop = sizeOfColumns

        sql = "INSERT INTO "..model.name
        local sqlColumns = " ("
        local sqlValues = " VALUES ("

        if model.belongsTo ~= nil and type(model.belongsTo) == "table" then
            local sizeOfBelongsTo = table.getSize(model.belongsTo)
            local counterForLoop = sizeOfBelongsTo
            for foreignKey, foreignTableName in pairs(model.belongsTo) do
                -- verificar se existe um atributo model[foreignkey]
                local columnName = foreignKey.."_id"
                local columnValue = "null"
                if model[foreignKey] ~= nil and
                   type(model[foreignKey]) == "table" and
                   model[foreignKey].rowid ~= nil and
                   type(model[foreignKey].rowid) == "number" then
                    columnValue = model[foreignKey].rowid
                end

                sqlColumns = sqlColumns..""..columnName
                sqlValues = sqlValues..""..columnValue

                if counterForLoop ~= 0 then
                    sqlColumns = sqlColumns..","
                    sqlValues = sqlValues..","
                end

                counterForLoop = counterForLoop-1
            end
        end

        for columnName, columnValue in pairs(columnValues) do

            sqlColumns = sqlColumns..""..columnName
            sqlValues = sqlValues..""..columnValue
            counterForLoop = counterForLoop-1

            if counterForLoop == 0 then
                sqlColumns = sqlColumns..")"
                sqlValues = sqlValues..")"
            else
                sqlColumns = sqlColumns..","
                sqlValues = sqlValues..","
            end
        end

        sql = sql..sqlColumns..sqlValues
    end

    return sql
end

function Table:_generateSQLSelect(model)
    local sql = nil
    if model ~= nil and model.name ~= nil and type(model.name) == "string" then
        --sql = "SELECT "..model.name..".rowid, * FROM "..model.name
        sql = "SELECT t1.rowid as "..model.name.."_rowid, "--..", a.*, b.* FROM "..model.name.." as a"

        local sizeOfColumns = table.getSize(model.columns)
        local counterForLoop = sizeOfColumns
        -- this counter will be used to differentiate columns for every table when joining
        local tableCount = 2
        for columnName, definitions in pairs(model.columns) do
            sql = sql.."t1."..columnName.." as "..model.name.."_"..columnName
            counterForLoop = counterForLoop-1
            if counterForLoop ~= 0 then
                sql = sql..","
            end

        end

        local foreignKeySQL = ""

        if model.belongsTo ~= nil and type(model.belongsTo) == "table" then

            -- SELECT a.rowid as a_rowid, * FROM Casa as a LEFT OUTER JOIN Pessoa as b on a.pessoa_id = b.rowid

            for foreignKey, foreignTable in pairs(model.belongsTo) do
                foreignKeySQL = foreignKeySQL.." LEFT OUTER JOIN "
                --sql = sql..foreignTable.." ON "..model.name.."."..foreignKey.."_id = "..foreignTable..".rowid"
                foreignKeySQL = foreignKeySQL..foreignTable.." as t"..tableCount.." ON t1."..foreignKey.."_id = t"..tableCount..".rowid"
                -- TODO: ao recuperar do servidor, então construir para o JOIN
                    -- "SELECT t1.rowid as Casa_rowid, t1.nome as Casa_nome,t1.idade as Casa_idade, t2.sobrenome as Pessoa_sobrenome, t2.cpf as Pessoa_cpf FROM Casa as t1 LEFT OUTER JOIN Pessoa as t2 ON t1.pessoa_id = t2.rowid"
                local myClass
                if DEBUG == false then
                    myClass  = require("models."..foreignTable)
                else
                    myClass  = require(foreignTable)
                end

                -- TODO: do validations on foreigntable to check if it contains columns, name and such.

                sql = sql..", t"..tableCount..".rowid as "..foreignKey.."_rowid"

                for columnName, definitions in pairs(myClass.columns) do
                    sql = sql..", t"..tableCount.."."..columnName.." as "..foreignKey.."_"..columnName
                end
                tableCount = tableCount+1
            end


        end
        sql = sql.." FROM "..model.name.." as t1"
        sql = sql..foreignKeySQL

        -- where e or
        if model.whereClause ~= nil then
            if type(model.whereClause) ~= "string" then
                error("An invalid WHERE clause was passed.")
            end

            if model.orClause ~= nil and type(model.orClause) ~= "string" then
                error("An invalid OR clause was passed.")
            end

            sql = sql..model.whereClause
            if model.orClause ~= nil then
                sql = sql..model.orClause
            end

        end



    else
        error("A model was expected to be different from nil and as a lua table. Also it must have a name.")
    end

    return sql
end

function Table:_generateSQLDelete(model)
    local sql = nil
    if model ~= nil and model.name ~= nil and type(model.name) == "string" and model.rowid ~= nil then
        sql = "DELETE FROM "..model.name.." WHERE rowid = "..model.rowid
    else
        error("A model was expected to be different from nil and as a lua table. Also it must have a name and a rowid.")
    end

    return sql
end

-- SELECT t1.rowid as Casa_rowid, t1.nome as Casa_nome,t1.idade as Casa_idade, t2.rowid as pessoa_rowid, t2.sobrenome as pessoa_sobrenome, t2.cpf as pessoa_cpf FROM Casa as t1 LEFT OUTER JOIN Pessoa as t2 ON t1.pessoa_id = t2.rowid WHERE t1.pessoa_id = 1

--[[
-- Creates a SQL in format: column = value for use in WHERE.
 ]]
function Table:_generateColumnValueForConditions(conditions, separator)
    if type(separator) ~= "string" or separator == "" then
        error("You passed a invalid separator. It must be a string and not be empty.")
    end
    local sql = ""
    local sizeOfColumns = table.getSize(conditions)
    local counterForLoop = sizeOfColumns
    for column, value in pairs(conditions) do


        if type(value) == "string" then
            value = "'"..value.."'"
        end

        sql = sql.." t1."..column.." = "..value
        counterForLoop = counterForLoop-1
        if counterForLoop ~= 0 then
            sql = sql.." "..separator
        end
    end

    return sql
end

-- TODO: fazer com que as conditions estejam associadas ao model

function Table:_generateSQLWhere(conditions)

    if conditions == nil or type(conditions) ~= "table" then
        error("Cannot create where clause without conditions.")
    end
    local sizeOfColumns = table.getSize(conditions)
    if sizeOfColumns == 0 then
        error("Cannot create where clause with empty conditions.")
    end

    local sql = " WHERE"
    sql = sql..self:_generateColumnValueForConditions(conditions, 'AND')

    return sql
end

function Table:_generateSQLWhereOR(conditions)
    if conditions == nil or type(conditions) ~= "table" then
        error("Cannot create where clause without conditions.")
    end

    local sizeOfColumns = table.getSize(conditions)
    if sizeOfColumns == 0 then
        error("Cannot create where clause with empty conditions.")
    end

    local sql = " OR"
    sql = sql..self:_generateColumnValueForConditions(conditions, 'OR')

    return sql
end

function Table:_generateSQLUpdate(model)
    local sql
    local columnValues = self:_extractValuesForColumnsFromModel(model)
    local sizeOfColumns = table.getSize(model.columns)
    local counterForLoop = sizeOfColumns
    if model ~= nil and model.name ~= nil and type(model.name) == "string" and model.rowid ~= nil then

        sql = "UPDATE "..model.name.." SET"

        for columnName, columnValue in pairs(columnValues) do
            sql = sql.." "..columnName.." = "..columnValue
            counterForLoop = counterForLoop-1
            if counterForLoop == 0 then
                sql = sql..""
            else
                sql = sql..","
            end
        end
        if model.belongsTo ~= nil and type(model.belongsTo) == "table" then
            for foreignKey, foreignTable in pairs(model.belongsTo) do
                if model[foreignKey] ~= nil and type(model[foreignKey]) == "table" and model[foreignKey].rowid ~= nil then
                    sql = sql..", "..foreignKey.."_id = "..model[foreignKey].rowid
                else
                    sql = sql..", "..foreignKey.."_id = null"
                end
            end
        end

        sql = sql.." WHERE rowid = "..model.rowid
    else
        error("A model was expected to be different from nil and as a lua table. Also it must have a name and a rowid.")
    end

    return sql
end

return Table
