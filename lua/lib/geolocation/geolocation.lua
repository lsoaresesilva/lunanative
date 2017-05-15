--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 20/04/17
-- Time: 14:43
-- To change this template use File | Settings | File Templates.
--

local Observable = require("lib.util.observable")
GeoLocation = Observable:extend("GeoLocation")
local instance = GeoLocation()
GeoLocation.instance = instance
instance = nil

--[[
-- Notify observers when a location update happens.
-- Should not be called directly.
 ]]
function locationChanged()
    local location = GeoLocation.instance._nativeObject:getLocation()
    local event = GeoLocation:getEventFromLocation(location)

    GeoLocation.super.notify(GeoLocation.instance, event)
end

function GeoLocation:new()
    error("Cannot instantiate Geolocation. Call getInstance() instead")
end

--[[
--
]]
function GeoLocation:getInstance()

    if self.instance._nativeObject == nil then
        self.instance._nativeObject = GeoLocationNative:newGeoLocationNative()
    end

    return self.instance
end

function GeoLocation:_retrieveLocation(locationType, callback, options)
    if callback ~= nil and type(callback) == "function" then

        GeoLocation.super.attach(GeoLocation.instance, callback)
        if self.instance ~= nil and type(self.instance) == "table" and
                self.instance._nativeObject ~= nil then
            -- if not specified, minTime and minDistance will be set to default: 1minute or 200 meters.
            options = options or {}
            options.minTime = options.minTime or 0
            options.minDistance = options.minDistance or 100
            options.type = locationType or "oneUpdate"
            options.callback = locationChanged
            print("vai chamar")
            print(locationChanged)
            self.instance._nativeObject:retrieveLocation(options)

        end
    end
end

function GeoLocation:stop()
    if self.instance ~= nil and type(self.instance) == "table" and
            self.instance._nativeObject ~= nil then
        GeoLocation.instance._nativeObject:stop()
    end
end

--[[
-- Register your application to receive user's GPS positioning over a period of time or distance.
-- Location updates will be sent to a callback which will receive a parameter with two properties: lat and long.
-- You should use this method only if your application demands continous data about user's geolocation (such a Map application), as it consumes more battery.
--
-- @param callback - a function which will be called to receive user's geolocation.
-- @param options - a table with two keys: minTime and minDistance. minTime is the minimum time interval between location updates in milliseconds (long). minDistance minimum distance between location in meters.
-- @return void.
]]
function GeoLocation:startReceivingLocation(callback, options)

    GeoLocation:_retrieveLocation("multipleUpdate", callback, options)
end

function GeoLocation:getCurrentLocation(callback)
    GeoLocation:_retrieveLocation("oneUpdate", callback)
end

function GeoLocation:getEventFromLocation(location)
    if type(location) == "table" or type(location) == "userdata" then
        if location["getLatitude"] ~= nil and location["getLongitude"] ~= nil then
            local lat = location:getLatitude()
            local long = location:getLongitude()
            local event = {lat=lat, long=long}
            return event
        end
    end
    return nil
end

return GeoLocation