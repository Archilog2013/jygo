

$(document).ready(function(){

    test("Contient le script à tester", function() {
        ok($('script[src$="jygo.js"]').length > 0, "le fichier est là");
        ok($('input[id="login"]').length > 0, "Le input login n'existe pas");
        ok($('input[id="psw"]').length  > 0, "Le input psw n'existe pas");

    });



});