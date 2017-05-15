--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 19:07
-- To change this template use File | Settings | File Templates.
--

local textField = {

}

function textField:newTextField(options)
    local newTextField = {}
    setmetatable(newTextField, {__index=textField})
    newTextField._nativeObject = NativeInterface:newTextField(options)
    return newTextField
end

function textField:getText()
    return self._nativeObject:getText()
end

return textField

