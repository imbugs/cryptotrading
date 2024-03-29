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
currentTradingService.factory('getCryptoCoinIndexRange', function ($resource) {
    return $resource('rest/getCryptoCoinIndexRange/:id');
});

/**
 * Service returning the current crypto coin index range
 */
currentTradingService.factory('getCurrentCryptoRate', function ($resource) {
    return $resource('rest/currentCryptoRate/:id');
});

/**
 * Service returning wallet
*/
currentTradingService.factory('getWallet', function ($resource) {
    return $resource('rest/getWallet/:id');
});

/**
 * Service returning wallet history
 */
currentTradingService.factory('getWalletHistory', function ($resource) {
    return $resource('rest/getWalletHistory/:id');
});

/**
 * Service returning market orders
 */
currentTradingService.factory('getMarketOrders', function ($resource) {
    return $resource('rest/marketOrders/:id');
});

/**
 * Service to get the crypto coin data
 */
currentTradingService.factory('getBtcData', function ($resource) {
    return $resource('rest/getBtcData/:id');
});

/**
 * Service to get the Macd data
 */
currentTradingService.factory('getMacdData', function ($resource) {
    return $resource('rest/getMacdData/:id');
});

/**
 * Service to get logging data
 */
currentTradingService.factory('getLogging', function ($resource) {
    return $resource('rest/getLogging/:id/:level');
});

/**
 * Service to get trade conditions of a trade rule
 */
currentTradingService.factory('getTradeConditions', function ($resource) {
    return $resource('rest/getTradeConditions/:id');
});
