using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace ExploreCalifornia.Models
{
    public class Post
    {
        public long id { get; set; } // id til database

        private string _key; // key til at lede i DB istedet for ID

        public string Key
        {
            get
            {
                if(_key == null)
                {
                    _key = Regex.Replace(Title.ToLower(), "[^a-z0-9]", "-");
                }
                return _key;
            }
            set { _key = value; }
        }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name ="Post title")]
        [StringLength(100, MinimumLength = 5, ErrorMessage ="Title must be 5-100 long")]
        public string Title { get; set; }

        public string Author { get; set; }

        [Required]
        [DataType(DataType.MultilineText)]
        [MinLength(100, ErrorMessage = "Must be minumum 100 long")]
        public string Body  { get; set; }

        public DateTime Posted { get; set; }
    }
}
