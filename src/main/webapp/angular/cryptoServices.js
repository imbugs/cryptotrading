var currentTradingService = angular.module('cryptoTrading.services', ['ngResource']);

/**
 *  Service returning the currenct trading
 */
currentTradingService.factory('getCurrentTrading', function ($resource) {
    return $resource('rest/getCurrentTrading');
});

/**
 * Service returning the current crypto coin currency rate
 */
currentTradingService.factory('getCurrentCryptoRate', function ($resource) {
    return $resource('rest/currentCryptoRate/:id');
});
