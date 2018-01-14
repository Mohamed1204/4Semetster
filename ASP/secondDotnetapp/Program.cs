using System;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;

namespace secondDotnetapp
{
    class Program
    {
        static void Main(string[] args)
        {
            var host = WebHost.CreateDefaultBuilder(args).UseStartup<Startup>().Build();
            host.Run();
            //Console.WriteLine("Hello World!");
        }
    }
}
