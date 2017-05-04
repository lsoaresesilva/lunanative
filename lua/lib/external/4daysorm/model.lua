------------------------------------------------------------------------------
--                               Require                                    --
------------------------------------------------------------------------------

require('lib.external.4daysorm.class.global')
require("lib.external.4daysorm.tools.func")
local Table = require('lib.external.4daysorm.class.table')

------------------------------------------------------------------------------
--                                Constants                                 --
------------------------------------------------------------------------------
-- Global
ID = "id"
AGGREGATOR = "aggregator"
QUERY_LIST = "query_list"


------------------------------------------------------------------------------
--                              Model Settings                              --
------------------------------------------------------------------------------


DB = {
    -- ORM settings
    new = (DB.new == true),
    DEBUG = (DB.DEBUG == true),
    backtrace = (DB.backtrace == true),
    -- database settings
    type = DB.type or "sqlite3",

    -- if not set a database name
    name = DB.name or "database.db",

}

SQLITE = "sqlite3"

local sql, _connect

-- if DB.new then
--     BACKTRACE(INFO, "Remove old database")

--     if DB.type == SQLITE then
--         os.remove(DB.name)
--     else
--         _connect:execute('DROP DATABASE `' .. DB.name .. '`')
--     end
-- end

------------------------------------------------------------------------------
--                               Database                                   --
------------------------------------------------------------------------------

-- Database settings
db = {
    -- Satabase connect instance
    connect = _connect,

    -- Execute SQL query
    execute = function (self, query)
        BACKTRACE(DEBUG, query)

        local result = self.connect:execute(query)

        if result then
            return result
        else
            BACKTRACE(WARNING, "Wrong SQL query")
        end
    end,

    -- Return insert query id
    insert = function (self, query)
        local _cursor = self:execute(query)
        return 1
    end,

    -- get parced data
    rows = function (self, query, own_table)
        local _cursor = self:execute(query)
        local data = {}
        local current_row = {}
        local current_table
        local row

        if _cursor then
            row = _cursor:fetch({}, "a")

            while row do
                for colname, value in pairs(row) do
                    current_table, colname = string.divided_into(colname, "_")

                    if current_table == own_table.__tablename__ then
                        current_row[colname] = value
                    else
                        if not current_row[current_table] then
                            current_row[current_table] = {}
                        end

                        current_row[current_table][colname] = value
                    end
                end

                table.insert(data, current_row)

                current_row = {}
                row = _cursor:fetch({}, "a")
            end

        end

        return data
    end
}

return Table
