--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 20:17
-- To change this template use File | Settings | File Templates.
--
local button = require("lib.button")
local scene = require("lib.scene")
local layout = require("lib.layout")
local textField = require("lib.textField")

local myLayout = layout:newLayout({type="linearLayout", orientation="vertical"})
local myscene = scene:newScene({layout=myLayout})


--- desenhar 1 campo de textfield
local myResultField = textField:newTextField()
myLayout:insert(myResultField)

--- desenhar 4 botoes horizontalmente
local secondLayout = layout:newLayout({type="linearLayout", orientation="horizontal"})
local buttonNativeUM = button:newButton({id=12, text="1"})
local buttonNativeDOIS = button:newButton({id=12345, text="2"})
local buttonNativeTRES = button:newButton({id=123, text="3"})
local buttonNativeQUATRO = button:newButton({id=123, text="4"})
secondLayout:insert(buttonNativeUM)
secondLayout:insert(buttonNativeDOIS)
secondLayout:insert(buttonNativeTRES)
secondLayout:insert(buttonNativeQUATRO)
myLayout:insert(secondLayout)

--- desenhar 4 botões

local terceiroLayout = layout:newLayout({type="linearLayout", orientation="horizontal"})
local abuttonNativeUM = button:newButton({id=12, text="1"})
local bbuttonNativeDOIS = button:newButton({id=123, text="2"})
local cbuttonNativeTRES = button:newButton({id=123, text="3"})
local dbuttonNativeQUATRO = button:newButton({id=123, text="4"})
terceiroLayout:insert(abuttonNativeUM)
terceiroLayout:insert(bbuttonNativeDOIS)
terceiroLayout:insert(cbuttonNativeTRES)
terceiroLayout:insert(dbuttonNativeQUATRO)
myLayout:insert(terceiroLayout)

--local buttonNative = button:newButton({id=123456, text="Davi e aline meus amores"})
--local textoOlaMundo = textField:newTextField()

--myLayout:insert(buttonNative)



--local buttonNativeOutroMesmo = button:newButton({id=972, text="layout"})
--local buttonNativeOutroMesmoJaja = button:newButton({id=9832, text="lalala"})
--local newLayout = layout:newLayout({type="linearLayout", orientation="vertical"})
--newLayout:insert(buttonNativeOutroMesmo)
--newLayout:insert(buttonNativeOutroMesmoJaja)

--myscene:insert(newLayout)

return myscene
