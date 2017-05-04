

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

local Pessoa = Model:extend("Pessoa", { columns = {
    sobrenome = {},
    cpf = { notNull=true }
  },
    hasMany={casas='Casa'}
  })

function Pessoa:init(columns)
    Pessoa.super:init(columns, self)
end

return Pessoa