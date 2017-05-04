--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 09/04/17
-- Time: 23:02
-- To change this template use File | Settings | File Templates.
--
package.path = package.path..";../?.lua"
package.path = package.path..";../../external/?.lua"

require("column")

function resetColumn()
    Column.definition = nil
end

describe("Creating default values for columns", function()

    it("Should fail, because there is no column definitions", function()

        assert.has.errors(function ()
            Column:_validateParams()
        end)
    end)

    it("Should fail, because there is no column type", function()
        definition = {}
        assert.has.errors(function ()
            Column:_validateParams(definition)

        end)
        resetColumn()
    end)

    it("Should fail, because there is no column name", function()
        definition = {}
        definition.type = {}
        assert.has.errors(function ()
            Column:_validateParams(definition)
            -- reset value for future tests


        end)
        resetColumn()
    end)

    --[[it("Should create default values for column definition when they were not informed.",   function()
        local definition = {}
        definition.type = "varchar"
        definition.name = "nome"
        local expected = {length=255, notNull=false}

        local length, notNull = Column:_defineDefaultValues(definition)
        local result = Column.definition
        resetColumn()

        assert.are.equal(expected.length, length)
        assert.are.equal(expected.notNull, notNull)

    end)]]

end)

describe("Generating columns definitions", function()

    it("Should fail, because there is no column definitions, type and name", function()
        Column.definition = {}
        Column.definition.name = {}
        Column.definition.type = nil
        assert.has.errors(function () 
            Column:_generateSQL() 
        end)
        resetColumn()
    end)

    --it("Should fail, because there is no column definitions", function()
    --    assert.has.errors(function () Column:_generateSQL() end)
    --end)


end)

describe("Creating columns in SQL", function()
    
    it("Should return a string with column definition based on SQL specification for text", function()
        local parameters = {name="idade" }
        local column = Column(parameters)
        local sql = column:_generateSQL()
        assert.are.equal(" idade null", sql)
    end)
end)
--[[
describe("Creating columns in SQL", function()

    it("Should throw a error because I did not pass any parameter to textField", function()

        assert.has.errors(function() Column:charField() end)

    end)

    it("Should throw a error because I did not pass name to column", function()

        assert.has.errors(function()
            Column:charField({})
            resetColumn()
        end)

    end)

    it("Must return a table with the same properties from parameters passed to charField plus a property called type with value 'varchar'", function()

        local parameters = {name="nome" }
        local result = Column:charField(parameters)
        local expected = {name="nome", type = "varchar" }

        for k,v in pairs(result) do
            assert.are.equal(expected[k], result.definition[k])
        end


    end)

    it("Should return a string with column definition based on SQL specification for varchar", function()
        local parameters = {name="nome" }
        local column = Column:charField(parameters)
        local sql = column:_generateSQL()
        assert.are.equal(" nome varchar(255) not null", sql)
        local parameters = {name="nome", length=100, notNull=true }
        local column = Column:charField(parameters)
        local sql = column:_generateSQL()
        assert.are.equal(" nome varchar(100) null", sql)
    end)
  
    it("Should return a string with column definition based on SQL specification for int", function()
        local parameters = {name="idade" }
        local column = Column:intField(parameters)
        local sql = column:_generateSQL()
        assert.are.equal(" idade int(11) not null", sql)
    end)
  
  it("Should return a string with column definition based on SQL specification for text", function()
        local parameters = {name="descrição" }
        local column = Column:textField(parameters)
        local sql = column:_generateSQL()
        assert.are.equal(" descrição text not null", sql)
    end)
  
  
  

end)
]]
