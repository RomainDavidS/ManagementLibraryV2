PGDMP                 	         x         
   users_test    11.2    12.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    72631 
   users_test    DATABASE     �   CREATE DATABASE users_test WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';
    DROP DATABASE users_test;
             	   adm_users    false            �            1259    98382    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public       	   adm_users    false            �            1259    98384    role    TABLE     z   CREATE TABLE public.role (
    id bigint NOT NULL,
    name character varying(255),
    wording character varying(255)
);
    DROP TABLE public.role;
       public         	   adm_users    false            �            1259    98392    users    TABLE     	  CREATE TABLE public.users (
    id bigint NOT NULL,
    active boolean NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    phone character varying(255)
);
    DROP TABLE public.users;
       public         	   adm_users    false            �            1259    98400 
   users_role    TABLE     ]   CREATE TABLE public.users_role (
    id_user bigint NOT NULL,
    id_role bigint NOT NULL
);
    DROP TABLE public.users_role;
       public         	   adm_users    false                       2606    98391    role role_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.role DROP CONSTRAINT role_pkey;
       public         	   adm_users    false    197                       2606    98404 !   users uk6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     ]   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7;
       public         	   adm_users    false    198                       2606    98399    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public         	   adm_users    false    198                       2606    98410 &   users_role fkahsq7xiwuf9xb7261rw6bt3ir    FK CONSTRAINT     �   ALTER TABLE ONLY public.users_role
    ADD CONSTRAINT fkahsq7xiwuf9xb7261rw6bt3ir FOREIGN KEY (id_user) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.users_role DROP CONSTRAINT fkahsq7xiwuf9xb7261rw6bt3ir;
       public       	   adm_users    false    198    199    2054                       2606    98405 &   users_role fkjm2d8v3yts0ehg9pmly9hnn43    FK CONSTRAINT     �   ALTER TABLE ONLY public.users_role
    ADD CONSTRAINT fkjm2d8v3yts0ehg9pmly9hnn43 FOREIGN KEY (id_role) REFERENCES public.role(id);
 P   ALTER TABLE ONLY public.users_role DROP CONSTRAINT fkjm2d8v3yts0ehg9pmly9hnn43;
       public       	   adm_users    false    2050    197    199           