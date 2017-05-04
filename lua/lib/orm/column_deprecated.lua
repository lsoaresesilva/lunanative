--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 09/04/17
-- Time: 23:30
-- To change this template use File | Settings | File Templates.
-- COLUNA TIPO [(LENGTH)] [NULL]

local class = require("30log")

Column = class("Column")

function Column:init(definitions)
    
    --if self:_validateParams(definitions) then
    self.definition = definitions or {}
    --end
end

function Column:setValue(value)
  
end

function Column:_generateSQL()

    --if self:_validateParams(self.definition) then
          local notNullString
          if self.definition.notNull == true then
              notNullString = "not null"
          else
              notNullString = ""
          end
        local sql = " "..self.definition.name..""..notNullString
        return sql
    --end
end
--[[
function Column:_validateParams(params)
    if params == nil or type(params) ~= "table" then
        error("A table was expected with params for this column.")
    end

    if params.name == nil or type(params.name) ~= "string" then
        error("A column must have a valid name.")
    end

    return true
end

function Column:_defineDefaultValues(columnsDefinitions)
      local length
      
      if columnsDefinitions.length == nil then
        if columnsDefinitions.type == "varchar" then
            length = 255
        elseif columnsDefinitions.type == "int" then
            length = 11
        elseif columnsDefinitions.type == "text" then
            length = nil
        end
      else
          length = columnsDefinitions.length
      end
      local notNull = columnsDefinitions.notNull or false

      return length, notNull
end

function Column:charField(columnsDefinitions)
    local column = Column()
    
    if column:_validateParams(columnsDefinitions) then
        column.definition = columnsDefinitions
        column.definition.type = "varchar"
    end

    return column
end

function Column:intField(columnsDefinitions)
    local column = Column()
    
    if column:_validateParams(columnsDefinitions) then
        column.definition = columnsDefinitions
        column.definition.type = "int"
    end

    return column
end

function Column:textField(columnsDefinitions)
    local column = Column()
    
    if column:_validateParams(columnsDefinitions) then
        column.definition = columnsDefinitions
        column.definition.type = "text"
    end

    return column
end
]]

return Column

