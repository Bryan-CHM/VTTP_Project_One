-- Insert testusers
insert into user(username, password)
    values 
        ('testuser', sha1('password')),
        ('testuser2', sha1('password2')),
        ('testuser3', sha1('password3')),
        ('testuser4', sha1('password4')),
        ('testuser5', sha1('password5')),
        ('testuser6', sha1('password6')),
        ('testuser7', sha1('password7')),
        ('testuser8', sha1('password8')),
        ('testuser9', sha1('password9')),
        ('testuser10', sha1('password10'));
