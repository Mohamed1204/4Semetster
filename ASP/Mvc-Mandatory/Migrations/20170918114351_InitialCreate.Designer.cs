﻿// <auto-generated />
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage;
using mvc_mandatory.Models;
using System;

namespace mvcmandatory.Migrations
{
    [DbContext(typeof(MovieDbContext))]
    [Migration("20170918114351_InitialCreate")]
    partial class InitialCreate
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.0.0-rtm-26452");

            modelBuilder.Entity("mvc_mandatory.Models.Entities.Movie", b =>
                {
                    b.Property<int>("movieId")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("name");

                    b.Property<int>("rating");

                    b.Property<int>("year");

                    b.HasKey("movieId");

                    b.ToTable("Movies");
                });
#pragma warning restore 612, 618
        }
    }
}
