using Microsoft.EntityFrameworkCore;
using StudentsCatalog.Models.Entities;

namespace StudentsCatalog.Models
{
    public class StudentDbContext : DbContext
    {
        public DbSet<Student> Students { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionBuilder)
        {
            optionBuilder.UseSqlite("Filename=./StudentCatalog.db");
        } 
        
    }
    
}