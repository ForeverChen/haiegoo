1、dubbo运行方式使用独立Application加载Spring容器启动，不依赖Web容器
2、dubbo可以使用eclipse或idea运行、bat运行和shell运行
3、保证dubbo启动时只占一个端口：如20880
4、maven打包已解决，最终产出 *.jar(接口包), *-bin.zip(运行包)
5、不同的环境使用不同的配置文件已解决，分为dev(开发环境)、test(测试环境)、www(生产环境)
6、日志输入目录，打印在bin/logs下 