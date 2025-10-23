# Static Site Generator

This is a simple static site generator written in Scala. It takes markdown files as input and generates HTML files as output.

## Features
- Converts markdown files to HTML
- Each HTML node will have a unique AST type
- Validate any input and report errors at compile time on the offending line
- Output is text on STDOUT
- It takes as input the filename of the markdown file to be converted
- It's a command line application
- It uses functional Scala libraries (from Typelevel) and Parsley for parsing
- Uses cats effect IO for handling filesystem interactions and stdout output
- Uses Either for error handling, carrying the result value on the right and the information about the error on the error channel
- Uses newtypes for type safety

## Markdown syntax supported and relative HTML output 
| Text property   | Markdown Syntax          | HTML Output                   |
|-----------------|--------------------------|-------------------------------|
| H1              | `#`                      | `<h1>header</h1>`             |
| H2              | `##`                     | `<h2>header</h2>`             |
| H3              | `###`                    | `<h3>header</h3>`             |
| Bold            | `**bold text**`          | `<strong>bold text</strong>`  |
| Italic          | `*italic text*`          | `<em>italic text</em>`        |
| Links           | `[link text](url)`       | `<a href="url">link text</a>` |
| Underlined      | `__underlined text__`     | `<u>underlined text</u>`      |
| Paragraphs      | separated by blank lines | `<p>paragraph text</p>`       |
| Unordered lists | `- item`                 | `<ul><li>item</li></ul>`      | 
| Ordered lists   |  `1. item`               | `<ol><li>item</li></ol>`      | 


### Future stretch goals
- Support for code blocks: triple backticks or similar
- Support for images: `![alt text](image url)`
- Support for blockquotes: `> quote`
- Support line separators: e.g. `---`
- Support for tables
- Support coloured text
- Support anchor links
- Support nested lists





