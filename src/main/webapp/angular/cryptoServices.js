var currentTradingService = angular.module('cryptoTrading.services', ['ngResource']);

currentTradingService.factory('getCurrentTrading', function ($resource) {
    return $resource('rest/getCurrentTrading');
});

currentTradingService.factory('getCurrentCryptoRate', function ($resource) {
    return $resource('rest/currentCryptoRate/:id');
});
