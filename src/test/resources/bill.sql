INSERT INTO `seat_types` (`id`, `name`, `price`)
VALUES (1, 'Normal', 50000),
       (2, 'VIP', 70000),
       (3, 'string', 0);
INSERT INTO `seats` (`id`, `row_name`, `row_index`, `status`, `room_id`, `seat_type_id`)
VALUES (1, 'A', 1, 1, 1, 1),
       (2, 'A', 2, 1, 1, 1),
       (3, 'A', 3, 1, 1, 1);
INSERT INTO `roles` (`id`, `name`)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO `users` (`id`, `email`, `password`, `date_of_birth`, `role_id`, `verify`, `create_date`, `full_name`,
                     `verify_account`, `verify_pass`, `gender`, `phone_number`, `avatar`, `provider_id`,
                     `provider`)
VALUES (random_uuid(), 'admin@gmail.com',
        '$2a$10$iCIcjsW9T6r0havQ1gTLSua3zKSX6vEOW2P6ySNBuivVpID3ya152', NULL, 1, '1', '2023-11-23 14:19:45.829821',
        'admin', NULL, NULL, 'UNKNOWN', NULL,
        'https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&',
        NULL, 'LOCAL');
