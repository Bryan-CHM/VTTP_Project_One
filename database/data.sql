-- Insert 5 - 6 users
-- Try batch insert

insert into user(username, password)
    values 
        ('test', sha1('test')),
        ('testuser', sha1('password')),
        ('testuser2', sha1('password2')),
        ('testuser3', sha1('password3')),
        ('testuser4', sha1('password4'));