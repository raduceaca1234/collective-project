insert into admins(email, password) values ('email1', 'pass1'), ('email2', 'pass2');
insert into users(email, password, first_name, last_name, phone_number) values ('user_email1', 'user_pass1', 'first_name1', 'last_name1', 'phone_number1'), ('user_email2', 'user_pass2', 'first_name2', 'last_name2', 'phone_number2');
insert into announcements(owner_id, name, description, location, category, duration, status, price_per_day) values (1, 'name1', 'descr1', 'loc1', 0, 3, 0, 50), (1, 'name2', 'descr2', 'loc2', 0, 3, 0, 50);
-- insert into images(image_bytes, announcement_id) values (cast('image1' as varbinary(max)), 1), (cast('image2' as varbinary(max)), 1);
insert into wishlists(owner_id) values (1), (2);
insert into wishlist_items(wishlist_id, announcement_id) values (1, 1), (1, 2);
insert into discussions (interested_user_id, announcement_id) values (1, 1), (1, 2);
insert into loans(discussion_id) values (1);
insert into closed_loans(loan_id) values (1);