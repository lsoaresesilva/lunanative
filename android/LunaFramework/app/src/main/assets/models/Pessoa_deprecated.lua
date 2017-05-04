--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 08/04/17
-- Time: 15:42
-- To change this template use File | Settings | File Templates.
--

local ActiveRecord = require("lib.activeRecord")
local Pessoa = ActiveRecord:extend("Pessoa")

function Pessoa:init(values)
    print("init")
    self:createTable({
        {name="nome", type="varchar" }
    })
    Pessoa.super.init(self, values)
end



return Pessoa

