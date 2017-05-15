--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 13/05/17
-- Time: 15:13
-- To change this template use File | Settings | File Templates.
--

function throw()
    error(message(errorHandling:code()))
end


errorHandling = LunaError:getInstance()
errorHandling:define(throw)

function message(code)
    if code == 1 then
        return "Missing properties for instance creation."
    elseif code == 2 then
        return "Missing properties for REST request."
    elseif code == 3 then
        return "Invalid method for REST request."
    elseif code == 4 then
        return "Invalid URL."
    elseif code == 5 then
        return "Failed to convert response to JSON."
    elseif code == 6 then
        return "A callback was not provided."
    elseif code == 7 then
        return "Missing text property for Button creation."
    elseif code == 8 then
        return "Image not found."
    elseif code == 9 then
    elseif code == 10 then
    elseif code == 11 then
        return "Missing table for LunaHashMap creation."
    elseif code == 12 then
        return "Missing function for LunaFunction creation."
    end

end

