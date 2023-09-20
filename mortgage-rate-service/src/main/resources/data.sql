INSERT INTO MORTGAGE_PROFILE (ID, amortization_period, profile_rating, mortgage_type)
VALUES (1, 5, 'A', 'O');
INSERT INTO MORTGAGE_PROFILE (ID, amortization_period, profile_rating, mortgage_type)
VALUES (2, 10, 'A', 'O');
INSERT INTO MORTGAGE_PROFILE (ID, amortization_period, profile_rating, mortgage_type)
VALUES (3, 5, 'B', 'C');
INSERT INTO MORTGAGE_PROFILE (ID, amortization_period, profile_rating, mortgage_type)
VALUES (4, 10, 'B', 'C');
INSERT INTO mortgage_rate (ID, mortgage_rate, mortgage_profile_id)
VALUES(100, 2.9, 1);
INSERT INTO mortgage_rate (ID, mortgage_rate, mortgage_profile_id)
VALUES(101, 3, 2);
INSERT INTO mortgage_rate (ID, mortgage_rate, mortgage_profile_id)
VALUES(102, 1.1, 3);
INSERT INTO mortgage_rate (ID, mortgage_rate, mortgage_profile_id)
VALUES(103, 4.2, 4);