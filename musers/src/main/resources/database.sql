PGDMP                 	         x         
   users_test    11.2    12.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    72631 
   users_test    DATABASE     �   CREATE DATABASE users_test WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';
    DROP DATABASE users_test;
             	   adm_users    false            �          0    98384    role 
   TABLE DATA           1   COPY public.role (id, name, wording) FROM stdin;
    public       	   adm_users    false    197   �       �          0    98392    users 
   TABLE DATA           Z   COPY public.users (id, active, email, first_name, last_name, password, phone) FROM stdin;
    public       	   adm_users    false    198   �       �          0    98400 
   users_role 
   TABLE DATA           6   COPY public.users_role (id_user, id_role) FROM stdin;
    public       	   adm_users    false    199   �       �           0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);
          public       	   adm_users    false    196            �   M   x��5���q�wt����tL����,.)J,I--��5�J:��:��q���&�r�C�C�]�8CK2s2�!b���� ��      �   �   x��ͻ�0 ��}
Vn�n!�AI�H4.*!P�*o�� �g����"h�(��߳5����*}�������oz��0'
=8�iT�y���;��HɲaZ��5�r�F�-'�eZ��k�@�ԅ0L��������A��T�yyW1+�ì�η��f%Ѝ���H��A_k�wJ      �      x��5��5��5��5��5�c���� '     