insert into Nomrole(name, level)
    values('employee', 1);
insert into Nomrole(name, level)
    values('customer', 2);

    
insert into Nomcountry(name)
    values('France');
insert into Nomcountry(name)
    values('Italy');
    
insert into Nomcity(name, country)
    values('Paris', 'France');
insert into Nomcity(name, country)
    values('Milan', 'Italy');
insert into Nomcity(name, country)
    values('Rome', 'Italy');


insert into Users(name, surname, email, password, address, phone, role)
    values(
    'Riccardo', 'Desantis',
    'effetti@gmail.com',
    'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86',
    'piazza Leonardo da Vinci, Milano',
    '+39 333 1234567',
    'employee');
insert into Users(name, surname, email, password, address, phone, role)
    values(
    'Pasquale', 'Desantis',
    'lino@gmail.com',
    'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86',
    'via Porpora 42, Milano',
    '+39 333 1234567',
    'customer');
    
insert into Nomcomponent(name, parameters)
    values('Flight',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"xxx\"
    }
}');
insert into Nomcomponent(name, parameters)
    values('Train',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"xxx\"
    }
}');
insert into Nomcomponent(name, parameters)
    values('Car Rental',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"xxx\"
    }
}');
insert into Nomcomponent(name, parameters)
    values('Hotel',
'{
    \"nights\": {
        \"type\": \"java.lang.Long\",
        \"value\": \"0\"
    }
}');
insert into Nomcomponent(name, parameters)
    values('Excursion', '{}');


insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Flight from Milan LIN to Paris CDG',
    'This flight, from Milan Linate to Paris Charles de Gaulle, is offered by Alitalia.',
    'Flight',
    'Milan',
    '50',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"Paris\"
    }
}');

insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan LIN to Paris CDG',
    '2014-06-12 6:55:00',
    '2014-06-12 8:25:00'
    );

insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan LIN to Paris CDG',
    '2014-06-12 7:40:00',
    '2014-06-12 9:10:00'
    );

insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan LIN to Paris CDG',
    '2014-06-12 13:00:00',
    '2014-06-12 14:30:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan LIN to Paris CDG',
    '2014-06-12 15:30:00',
    '2014-06-12 17:00:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan LIN to Paris CDG',
    '2014-06-12 17:10:00',
    '2014-06-12 18:40:00'
    );

insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Flight from Paris CDG to Milan LIN',
    'This flight, from Paris Charles de Gaulle to Milan Linate, is offered by Alitalia.',
    'Flight',
    'Paris',
    '50',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"Milan\"
    }
}');

insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Visita guidata al Duomo',
    'Visita al duomo.',
    'Excursion',
    'Milan',
    '30',
'{}');

insert into Datecomponent(component, start, end)
    values(
    'Flight from Paris CDG to Milan LIN',
    '2014-06-12 6:55:00',
    '2014-06-12 8:25:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Paris CDG to Milan LIN',
    '2014-06-12 7:40:00',
    '2014-06-12 9:10:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Paris CDG to Milan LIN',
    '2014-06-12 13:00:00',
    '2014-06-12 14:30:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Paris CDG to Milan LIN',
    '2014-06-12 15:30:00',
    '2014-06-12 17:00:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Paris CDG to Milan LIN',
    '2014-06-12 17:10:00',
    '2014-06-12 18:40:00'
    );

insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Hotel La Maison Champs-Elysees',
    'This is a 5-stars hotel in Paris, address 8 Rue Jean Goujon, 75008 Paris.',
    'Hotel',
    'Paris',
    '100',
'{
    \"nights\": {
        \"type\": \"java.lang.Long\",
        \"value\": \"4\"
    }
}');
insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Hotel Hilton Milano',
    'This is the Hilton Hotel in Milan!',
    'Hotel',
    'Milan',
    '120',
'{
    \"nights\": {
        \"type\": \"java.lang.Long\",
        \"value\": \"4\"
    }
}');
insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Albergo Miramare',
    'A great albergo in Rome.',
    'Hotel',
    'Rome',
    '60',
'{
    \"nights\": {
        \"type\": \"java.lang.Long\",
        \"value\": \"4\"
    }
}');

insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Excursion to the Eiffel Tower',
    'A tour to the Eiffel Tower.',
    'Excursion',
    'Paris',
    '10',
    '{}');
insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Excursion to Montmartre',
    'A tour to Montmartre.',
    'Excursion',
    'Paris',
    '0',
    '{}');
insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Excursion to the Louvre Museum',
    'A tour to the Louvre Museum.',
    'Excursion',
    'Paris',
    '20',
    '{}');
    
insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Train from Paris CDG to Paris Center',
    'This train will help you reach the center of Paris in minutes, leaving from the Charles de Gaulle airport every 30 minutes.',
    'Train',
    'Paris',
    '14',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"Paris\"
    }
}');

insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Train from Paris Center to Paris CDG',
    'This train will take you to the Charles de Gaulle airport in minutes, leaving from the center of Paris every 30 minutes.',
    'Train',
    'Paris',
    '14',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"Paris\"
    }
}');
    
insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Flight from Milan MXP to Rome FCO',
    'This flight, from Milan Malpensa to Rome Fiumicino, is offered by Alitalia.',
    'Flight',
    'Milan',
    '60',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"Rome\"
    }
}');
insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan MXP to Rome FCO',
    '2014-06-12 6:55:00',
    '2014-06-12 8:25:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan MXP to Rome FCO',
    '2014-06-12 7:40:00',
    '2014-06-12 9:10:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan MXP to Rome FCO',
    '2014-06-12 13:00:00',
    '2014-06-12 14:30:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan MXP to Rome FCO',
    '2014-06-12 15:30:00',
    '2014-06-12 17:00:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Milan MXP to Rome FCO',
    '2014-06-12 17:10:00',
    '2014-06-12 18:40:00'
    );

insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Flight from Rome FCO to Milan MXP',
    'This flight, from Rome Fiumicino to Milan Malpensa, is offered by Alitalia.',
    'Flight',
    'Rome',
    '60',
'{
    \"to\": {
        \"type\": \"java.lang.String\",
        \"value\": \"Milan\"
    }
}');
insert into Datecomponent(component, start, end)
    values(
    'Flight from Rome FCO to Milan MXP',
    '2014-06-12 6:55:00',
    '2014-06-12 8:25:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Rome FCO to Milan MXP',
    '2014-06-12 7:40:00',
    '2014-06-12 9:10:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Rome FCO to Milan MXP',
    '2014-06-12 13:00:00',
    '2014-06-12 14:30:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Rome FCO to Milan MXP',
    '2014-06-12 15:30:00',
    '2014-06-12 17:00:00'
    );
insert into Datecomponent(component, start, end)
    values(
    'Flight from Rome FCO to Milan MXP',
    '2014-06-12 17:10:00',
    '2014-06-12 18:40:00'
    );

insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Excursion to the Vatican Museums',
    'A tour to the Vatican Museums.',
    'Excursion',
    'Rome',
    '50',
    '{}');
insert into Components(name, description, typeComponent, city, price, defaults)
    values(
    'Excursion to the Colosseo',
    'A tour to the Colosseo.',
    'Excursion',
    'Rome',
    '20',
    '{}');
    
insert into Packages(name, days, description)
    values(
    'Milan-Paris 5d',
    '5',
    'The package represent a 5 days journey, travelling from Milan to Paris.'
    );
    
insert into Packages(name, days, description)
    values(
    'Milan-Rome 4d',
    '4',
    'The package represent a 4 days journey, travelling from Milan to Rome.'
    );
insert into Packages(name, days, description)
    values(
    'Milan 1d',
    '1',
    'Fake packet.'
    );
    
insert into ComponentsInPackage(component, package)
    values(
    'Flight from Milan LIN to Paris CDG',
    'Milan-Paris 5d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Train from Paris CDG to Paris Center',
    'Milan-Paris 5d'
    );
    
insert into ComponentsInPackage(component, package)
    values(
    'Hotel La Maison Champs-Elysees',
    'Milan-Paris 5d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Excursion to the Eiffel Tower',
    'Milan-Paris 5d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Excursion to Montmartre',
    'Milan-Paris 5d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Excursion to the Louvre Museum',
    'Milan-Paris 5d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Train from Paris Center to Paris CDG',
    'Milan-Paris 5d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Flight from Paris CDG to Milan LIN',
    'Milan-Paris 5d'
    );
    
insert into ComponentsInPackage(component, package)
    values(
    'Flight from Milan MXP to Rome FCO',
    'Milan-Rome 4d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Albergo Miramare',
    'Milan-Rome 4d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Excursion to the Vatican Museums',
    'Milan-Rome 4d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Excursion to the Colosseo',
    'Milan-Rome 4d'
    );
insert into ComponentsInPackage(component, package)
    values(
    'Flight from Rome FCO to Milan MXP',
    'Milan-Rome 4d'
    );
    
insert into CustomPackages(configuration, days, package, user)
	values(
'{
    \"Flight from Milan LIN to Paris CDG\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12\"
        },
        \"time\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12 7:40:00\"
        }
    },
    \"Train from Paris CDG to Paris Center\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12\"
        }
    },
    \"Hotel La Maison Champs-Elysees\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12\"
        },
        \"nights\": {
            \"type\": \"java.lang.Long\",
            \"value\": \"5\"
        }
    },
    \"Excursion to the Eiffel Tower\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-13\"
        }
    },
    \"Excursion to Montmartre\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-14\"
        }
    },
    \"Excursion to the Louvre Museum\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-15\"
        }
    },
    \"Train from Paris Center to Paris CDG\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-17\"
        }
    },
    \"Flight from Paris CDG to Milan LIN\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-17\"
        },
        \"time\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-17 15:30:00\"
        }
    }
}',
	'7',
	'Milan-Paris 5d',
	'lino@gmail.com'
	);
	
insert into CustomPackages(configuration, days, package, user)
	values(
'{
    \"Flight from Milan LIN to Paris CDG\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12\"
        },
        \"time\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12 7:40:00\"
        }
    },
    \"Train from Paris CDG to Paris Center\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12\"
        }
    },
    \"Hotel La Maison Champs-Elysees\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-12\"
        },
        \"nights\": {
            \"type\": \"java.lang.Long\",
            \"value\": \"5\"
        }
    },
    \"Excursion to the Eiffel Tower\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-13\"
        }
    },
    \"Excursion to Montmartre\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-14\"
        }
    },
    \"Excursion to the Louvre Museum\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-15\"
        }
    },
    \"Train from Paris Center to Paris CDG\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-17\"
        }
    },
    \"Flight from Paris CDG to Milan LIN\": {
        \"day\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-17\"
        },
        \"time\": {
            \"type\": \"java.util.Date\",
            \"value\": \"2014-06-17 15:30:00\"
        }
    }
}',
	'7',
	'Milan-Paris 5d',
	'lino@gmail.com'
	);