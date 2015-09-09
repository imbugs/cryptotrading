var currentTradingService = angular.module('cryptoTrading.services', ['ngResource']);

currentTradingService.factory('getCurrentTrading', function ($resource) {
    return $resource('rest/getCurrentTrading');
});
