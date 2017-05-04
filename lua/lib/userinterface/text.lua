--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 19:07
-- To change this template use File | Settings | File Templates.
--

local textInput = {

}

function textInput:newText(options)
    local newText = {}
    setmetatable(newText, {__index=textInput})
    newText._nativeObject = NativeInterface:newText(options)
    return newText
end



return textInput

