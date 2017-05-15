--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 19:07
-- To change this template use File | Settings | File Templates.
--

local dateTime = {

}

function dateTime:newDateTime(options)
    local newDateTime = {}
    setmetatable(newDateTime, {__index=dateTime})
    newDateTime._nativeObject = NativeInterface:newDateTime(options)
    return newDateTime
end

return dateTime

