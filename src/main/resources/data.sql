INSERT INTO users (id, name, email, password, available_credit)
VALUES (1, 'Rishabh', 'rishabh@gmail.com', 'abc', 800000);
INSERT INTO users (id, name, email, password, available_credit)
VALUES (2, 'TestUser', 'testuser@gmail.com', 'abc', 100000);

INSERT INTO contract (id, rate, status, term_length, total_available, per_payment, name, type)
VALUES (100, 12.0, 'AVAILABLE', 12, 126722.64, 12000.25, 'Contract 1', 'MONTHLY');
INSERT INTO contract (id, rate, status, term_length, total_available, per_payment, name, type)
VALUES (300, 12.0, 'AVAILABLE', 12, 63360.00, 6000.00, 'Contract 3', 'MONTHLY');
INSERT INTO contract (id, rate, status, term_length, total_available, per_payment, name, type)
VALUES (400, 12.0, 'AVAILABLE', 12, 63360.00, 6000.00, 'Contract 4', 'MONTHLY');
INSERT INTO contract (id, rate, status, term_length, total_available, per_payment, name, type)
VALUES (500, 12.0, 'AVAILABLE', 12, 63360.00, 6000.00, 'Contract 5', 'MONTHLY');
INSERT INTO contract (id, rate, status, term_length, total_available, per_payment, name, type)
VALUES (600, 12.0, 'AVAILABLE', 12, 63360.00, 6000.00, 'Contract 6', 'MONTHLY');

