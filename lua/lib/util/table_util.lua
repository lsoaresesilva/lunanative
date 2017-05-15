--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 11/04/17
-- Time: 21:40
-- To change this template use File | Settings | File Templates.
--

function table.getSize(table)
    local n = 0
    for k, v in pairs(table) do
        n = n+1
    end
    return n
end

