using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Google_logIn.Models
{
    public class Topic
    {
        public long Id { get; set; }
        public string Name { get; set; }

        public ICollection<Post> Posts { get; set; }
    }
}
