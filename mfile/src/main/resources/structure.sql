PGDMP     9    8            	    w            files    11.2    12.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    68643    files    DATABASE     �   CREATE DATABASE files WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';
    DROP DATABASE files;
                adm_file    false            �            1259    68644    cover    TABLE     �   CREATE TABLE public.cover (
    id character varying(255) NOT NULL,
    data oid,
    file_name character varying(255),
    file_size bigint,
    file_type character varying(255),
    use character varying(255)
);
    DROP TABLE public.cover;
       public            adm_file    false            �           2606    68651    cover cover_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.cover
    ADD CONSTRAINT cover_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.cover DROP CONSTRAINT cover_pkey;
       public            adm_file    false    196           