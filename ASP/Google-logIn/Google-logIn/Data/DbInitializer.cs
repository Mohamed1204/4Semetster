using Google_logIn.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Google_logIn.Data
{
    public static class DbInitializer
    {
        public static void Initialize(PostDbContext context)
        {
            context.Database.EnsureCreated();

            // Look for any students.
            if (context.Posts.Any())
            {
                return;   // DB has been seeded
            }

            var topic = new Topic
            {
                Name = "politik",
            };

            var post = new Post
            {
            Author="Alexander", Posted=DateTime.Parse("2005-09-01"), Body="hva så", Title="shit", Topic=topic
            };
            
           
            context.Posts.Add(post);
            context.SaveChanges();

           
        }
    }
}
