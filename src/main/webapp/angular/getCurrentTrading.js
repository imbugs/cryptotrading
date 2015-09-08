var currentTradingService = angular.module('currentTradingService', ['ngResource']);

currentTradingService.factory('getCurrentTrading', function($resource) {
    return $resource('rest/getCurrentTrading');
});