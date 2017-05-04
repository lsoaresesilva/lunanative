

--DEBUG = true
local class
if DEBUG == false then
    class = require("lib.external.30log")
    require("lib.orm.model")
else
    class = require("30log")
    require("table_util")
    require("model")
end

local Casa = Model:extend("Casa", { columns = {
    nome = {},
    idade = { notNull=true }
  },
  belongsTo={
      pessoa='Pessoa'
  }
  })

function Casa:init(columns)
    Casa.super:init(columns, self)
end

return Casa