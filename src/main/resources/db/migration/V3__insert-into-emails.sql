insert into emails (receiver)
VALUES ('nik.malykh.2024@mail.ru')
on conflict (receiver) do nothing;