--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 11:44
-- To change this template use File | Settings | File Templates.
--

local button = {

}

-- This nativebutton must implement a Generic Interface between Android and iOS
function button:newButton(options)
    local newButton = {}
    setmetatable(newButton, {__index=button})
    newButton._nativeObject = NativeInterface:newButton("lua", options)
    return newButton
end

function button:setTouchCallback(callback)
    self._nativeObject:setTouchCallback(callback)
end

return button

