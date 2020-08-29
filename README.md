

## homepage/group/services更新方法

使用[markdown](https://www.markdownguide.org/getting-started/)语法进行文本录入与更新

## Teaching更新方法

根据```_teaching```目录中的文件新建```year-season-teaching-num.md```，根据已有内容复制更改内部信息

## publication更新方法

1. 根据```_publication```目录当中的文件新建文件，复制更改；
2. 将publication更新到tsv表格当中，运行```markdown_generator```中的```publications.py```自动生成；
3. 将journal文章的bib收集到```pubs.bib```，conference文章的bib收集到```proceedings.bib```，运行```pubsFromBib.py```自动生成