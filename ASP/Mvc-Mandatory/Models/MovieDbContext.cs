using Microsoft.EntityFrameworkCore;
using mvc_mandatory.Models.Entities;
namespace mvc_mandatory.Models
{
    

public class MovieDbContext : DbContext
{

 DbSet<Movie> Movies { get; set; }

 protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) {
        optionsBuilder.UseSqlite("Filename=./Movie.db");
    }   
}
}