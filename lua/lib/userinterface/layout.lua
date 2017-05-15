--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 01/04/17
-- Time: 18:43
-- To change this template use File | Settings | File Templates.
----
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 19:06
-- To change this template use File | Settings | File Templates.
--

local layout = {}

--
-- Creates an instance of layout.
-- Receives a single argument which is a table with the following keys: type and orientation. At the moment only linearLayout is supported as type and orientation is a string which assumes values for "vertical" or "horiozontal".
--
function layout:newLayout(options)
    local newLayout = {}
    setmetatable(newLayout, {__index=layout})
    newLayout._nativeObject = NativeInterface:newLayout(options)
    return newLayout
end


--
-- Insert a component into this layout. It's organization in the scene will be automatically made by this layout.
-- @param component An object of type Component.
function layout:insert(component)
    self._nativeObject:insert(component._nativeObject)
end

return layout



