// Angular filter type for Order Type
angular.module('orderTypeFilter', []).filter('orderType', function () {
    return function (input) {
        if (input == 'SELL') {
            return 'Verkoop'
        }
        else {
            return 'Koop';
        }
    };
});
