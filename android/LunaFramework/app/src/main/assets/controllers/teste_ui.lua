--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 26/04/17
-- Time: 10:40
-- To change this template use File | Settings | File Templates.
--

local scene = require("lib.userinterface.scene")
local layout = require("lib.userinterface.layout")
local button = require("lib.userinterface.button_px")

function olamundo()
    print("opa!");
end

local btn = button:newButton({img={normal="btn.png"}})
btn:setTouchCallback(olamundo)
local lt = layout:newLayout({type="linearLayout", orientation="vertical"})
lt:insert(btn)
local sc = scene:newScene({layout=lt})

return sc