--
-- A scene is an representation of a single "page/screen". An application must have at least one scene.
-- Components (buttons, texts, and such) are organized on scenes using a structure called layout.
-- Layouts are a mean of defining how an component will be presented on scene. Example: one bellow another.
-- On Lua a scene must be created inside 'controllers' folder as an lua file.
-- In this file it is needed to create an instance of Scene calling newScene() and passing as parameter a table with a key called layout.  The value os this key is an object of instance layout.
-- After an scene is created, components are added to the layout associated within the scene.
-- A scene must be returned

-- Example:
--[[local scene = require("lib.userinterface.scene")
local layout = require("lib.userinterface.layout")
local button = require("lib.userinterface.button")

local btn = button:newButton({img={normal="btn.png"}})
local lt = layout:newLayout({type="linearLayout", orientation="vertical"})
lt:insert(btn)
local sc = scene:newScene({layout=lt})

return sc
-- ]]
local scene = {}

--
-- Creates an instance of Scene.
-- @param options Receives a single argument which is a table with the following key: layout. This key receive as value an object from Layout class.
--
function scene:newScene(options)
    local newScene = {}
    local nativeScene = NativeInterface:newScene(options)
    setmetatable(newScene, {__index=scene})
    newScene._nativeObject = nativeScene
    return newScene
end

function scene:goToScene(nextScene)

    local ns = require("controllers."..nextScene)
    if ns == nil or type(ns) ~= "table" or
       ns._nativeObject == nil or
       type(ns._nativeObject["goToScene"]) ~= "function" then
        error("Failed to load controller "..nextScene);
    end

    ns._nativeObject:goToScene()
end

return scene

