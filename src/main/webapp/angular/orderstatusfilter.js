
// Angular filter type for order status
angular.module('orderStatusFilter', []).filter('orderStatus', function () {
    return function (input) {
        switch (input) {
            case  'EXECUTED'   :
                return 'Uitgevoerd';
            case  'EXECUTING'  :
                return 'In uitvoering';
            case  'CANCELLED'  :
                return 'Afgebroken';
            case  'PARTLYEXEC' :
                return 'Gedeeltelijk uitgevoerd';
            case  'OPEN'       :
                return 'Open';
            case  'RETRY'      :
                return 'Herhaal';
            case  'ERROR'      :
                return 'Fout opgetreden';
            default :
                return '-';
        }
    };
});