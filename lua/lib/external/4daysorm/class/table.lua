------------------------------------------------------------------------------
--                          Required classes                                --
------------------------------------------------------------------------------

local Select = require('lib.external.4daysorm.class.select')
local Query, QueryList = require('lib.external.4daysorm.class.query')
local fields = require('lib.external.4daysorm.tools.fields')


------------------------------------------------------------------------------
--                               Table                                      --
------------------------------------------------------------------------------

Table = {
    -- database table name
    __tablename__ = nil,

    -- Foreign Keys list
    foreign_keys = {},
}

-- This method create new table
-------------------------------------------
-- @table_instance {table} class Table instance
-- 
-- @table_instance.__tablename__ {string} table name
-- @table_instance.__colnames {table} list of column instances
-- @table_instance.__foreign_keys {table} list of foreign key
--                                        column instances
-------------------------------------------
function Table:create_table(table_instance)
    -- table information
    local tablename = table_instance.__tablename__
    local columns = table_instance.__colnames
    local foreign_keys = table_instance.__foreign_keys

    BACKTRACE(INFO, "Start create table: " .. tablename)

    -- other variables
    local create_query = "CREATE TABLE IF NOT EXISTS `" .. tablename .. "` \n("
    local counter = 0
    local column_query
    local result

    for _, coltype in pairs(columns) do
        column_query = "\n     `" .. coltype.name .. "` " .. coltype:_create_type()

        if counter ~= 0 then
            column_query = "," .. column_query
        end

        create_query = create_query .. column_query
        counter = counter + 1
    end

    for _, coltype in pairs(foreign_keys) do
        create_query = create_query .. ",\n     FOREIGN KEY(`" ..
                       coltype.name .. "`)" .. " REFERENCES `" ..
                       coltype.settings.to.__tablename__ ..
                       "`(`id`)"
    end

    create_query = create_query .. "\n)"

    return create_query
end

-- Create new table instance
--------------------------------------
-- @args {table} must have __tablename__ key
-- and other must be a column names
--------------------------------------
function Table.new(self, args)
    local colnames = {}
    local create_query

    self.__tablename__ = args.__tablename__
    args.__tablename__ = nil

    local Table_instance = {
        ------------------------------------------------
        --             Table info varibles            --
        ------------------------------------------------

        -- SQL table name
        __tablename__ = self.__tablename__,

        -- list of column names
        __colnames = {},

        -- Foreign keys list
        __foreign_keys = {},

        ------------------------------------------------
        --                Metamethods                 --
        ------------------------------------------------

        -- If try get value by name "get" it return Select class instance
        __index = function (self, key)
            if key == "get" then
                return Select(self)
            end

            local old_index = self.__index
            setmetatable(self, {__index = nil})

            key = self[key]

            setmetatable(self, {__index = old_index, __call = self.create})

            return key
        end,

        -- Create new row instance
        -----------------------------------------
        -- @data {table} parsed query answer data
        --
        -- @retrun {table} Query instance
        -----------------------------------------
        create = function (self, data)
            return Query(self, data)
        end,

        ------------------------------------------------
        --          Methods which using               --
        ------------------------------------------------

        -- parse column in correct types
        column = function (self, column)
            local tablename = self.__tablename__

            if Type.is.table(column) and column.__classtype__ == AGGREGATOR then
                column.colname = tablename .. column.colname
                column = column .. ""
            end

            return "`" .. tablename .. "`.`" .. column .. "`",
                   tablename .. "_" .. column
        end,

        -- Check column in table
        -----------------------------------------
        -- @colname {string} column name
        --
        -- @return {boolean} get true if column exist
        -----------------------------------------
        has_column = function (self, colname)
            for _, table_column in pairs(self.__colnames) do
                if table_column.name == colname then
                    return true
                end
            end

            BACKTRACE(WARNING, "Can't find column '" .. tostring(colname) ..
                               "' in table '" .. self.__tablename__ .. "'")
        end,

        -- get column instance by name
        -----------------------------------------
        -- @colname {string} column name
        --
        -- @return {table} get column instance if column exist
        -----------------------------------------
        get_column = function (self, colname)
            for _, table_column in pairs(self.__colnames) do
                if table_column.name == colname then
                    return table_column
                end
            end

            BACKTRACE(WARNING, "Can't find column '" .. tostring(column) ..
                               "' in table '" .. self.__tablename__ .. "'")
        end
    }

    -- Add default column 'id'
    args.id = fields.PrimaryField({auto_increment = true})

    -- copy column arguments to new table instance
    for colname, coltype in pairs(args) do
        coltype.name = colname
        coltype.__table__ = Table_instance

        table.insert(Table_instance.__colnames, coltype)

        if coltype.settings.foreign_key then
            table.insert(Table_instance.__foreign_keys, coltype)
        end
    end

    setmetatable(Table_instance, {
        __call = Table_instance.create,
        __index = Table_instance.__index
    })

    _G.All_Tables[self.__tablename__] = Table_instance

    -- Create ne table if need
    local sqlCreateTable = nil
    if DB.new then

        sqlCreateTable = self:create_table(Table_instance)
    end

    return Table_instance, sqlCreateTable
end

setmetatable(Table, {__call = Table.new})

return Table