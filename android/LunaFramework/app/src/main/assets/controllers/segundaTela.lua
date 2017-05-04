--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 20:17
-- To change this template use File | Settings | File Templates.
--
local button = require("lib.button")
local scene = require("lib.scene")

local buttonNative = button:newButton({id=123, text="Segunda tela"})

function irParaOutraTela()
    scene:goToScene("telaInicial")
end

buttonNative:setTouchCallback(irParaOutraTela)

otherScene = scene:newScene({type="linearLayout"})
otherScene:insert(buttonNative)

return otherScene
