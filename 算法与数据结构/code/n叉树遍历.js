let jsonTree = { name: '', children: [] };
let jsonNode = this.jsonTree;
    const stack = [];
    stack.push(jsonNode);
    while (stack.length !== 0) {
      jsonNode = stack.pop();
      if (jsonNode.children.length > 0) {
        const childrens = jsonNode.children.reverse();
        jsonNode.children = [];
        stack.push(jsonNode);
        for (const item of childrens) {
          stack.push(item);
        }
      } else {
        console.log(jsonNode.name);
      }
}