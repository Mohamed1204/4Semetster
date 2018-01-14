using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using Google_logIn.Data;
using Microsoft.EntityFrameworkCore;
using Google_logIn.Models;

namespace Google_logIn.Controllers 
{   
    
    public class ForumController : Controller
    {
        private readonly PostDbContext _context;

        public ForumController(PostDbContext context)
        {
            _context = context;
        }
        [Route("Forum/index")]
        public async Task<IActionResult> Index()
        {
            return View(await _context.Topics.ToListAsync());
        }


        [Route("Forum/TopicPosts/{topicName}")]
        public IActionResult TopicPosts(string topicName)  
        {
          

           var post =  _context.Posts.Where(x => x.Topic.Name ==topicName);
           return View(post);
        }


        // GET: Posts/Create
        [Route("Forum/Create/{topicName}")]
       public IActionResult Create(string topicName)
        {
            ViewBag.Name = topicName;
            return View();
        }

        // POST: Posts/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Title,Author,Body,Posted")] Post post)
        {
            //post.Posted = DateTime.Now;

            if (ModelState.IsValid)
            {
                _context.Posts.Add(post);
                await _context.SaveChangesAsync();
                //return RedirectToAction(nameof(Index));
            }
            return View(post);
        }
    }
}
