# Brief

This assignment is composed of two parts:

1. Study of the configuration language used by a specific network device (IP firewall like nftables or ipfw, or VPN gateway like ip xfrm or OpenVPN).
2. Development of a translator that recognizes the language syntax (or part of it, according to the complexity), and translates it into a JSON or XML document.

Other information:

•Possibility to grant more assignments with different languages.

•Possibility to extend the work with a related thesis work

# Questions

## Scope & Trade-offs

1. Q: regarding the scope of the assignment, the configuration languages for tools like OpenVPN or nftables are quite extensive. Given the workload, would you prefer us to build a simplified end-to-end version (covering a basic language study and a simple translator for the whole process), or should we focus on a deeper, more comprehensive implementation for just a specific subset of the syntax?

   A: simplified end-to-end version

2. Q: IP firewall like nftables or ipfw, or VPN gateway like ip xfrm or OpenVPN, which is better for this project? Or could you recommend a suitable configuration language?

   A: nftables

3. Q: Nftables(If choose) has a very large syntax. I plan to focus only on a core subset, like basic IPv4, TCP/UDP filtering rules, and simple actions (accept/drop). Is this scope sufficient for the assignment?\*\*

   A: It depends on the verefoo resposity requirement

4. Q: Could I use other Parser Generator Libirary or Framework(Except from Jflex/CUP,such as ANTLR v4)

   A: Jflex/CUP

## Learning Resources/Schedule

1. Q: To better prepare for the formalization and parsing parts of this project, could you recommend any specific research papers, websites, or reference materials that would be particularly helpful?

   A: A.V. Aho, M.S. Lam, R. Sethi, J.D. Ullman, “Compilers, Principles, Techniques, & Tools”, Pearson.

2. Q: When is the best time to start this project (taking into account the pace of the course)? Or complete the relevant section in line with the course schedule?

   A: after lab about CUP

3. Q: Just to quickly confirm the logistics: is there a specific hard deadline for the project submission? Also, if I encounter technical blockers during the development phase, is email the best way to reach out for guidance or just ask after every Tuesday's lecture?

   A: make a short prepsention reservation before each exam

## Requirements for Output and Evaluation

1. Output Data Structure: What are the requirements for the generated JSON schema format? Is a generic object representation acceptable?

   A: It depends on the verefoo github resposity requirement

2. Assessment Criteria: Regarding the final evaluation, will you utilize a set of "blind" test cases, or is the burden of proof on us to provide a suite of test cases demonstrating our tool's reliability? Should I prepare my own set of valid and invalid configuration files to demonstrate error handling?

   A: It depends on the verefoo github resposity requirement

3. Handle Error: To what extent should a translator handle configuration files containing syntax errors? Should it simply throw an exception and terminate, or should it, like a real compiler, specify the exact line number and type of error?
   A: like a real compiler, specify the exact line number and type of error

# Milestones & Deliverables

1.get the mininum size support subset of nftables according to verefoo project.The deliverable is a txt file, which includes supported keyword and xml tag map link table.

2. Lexical analysis. The deliverable is a jflex file, which should handle Lexical Error and throw precise error line and column
3. Syntax analysis. The deliverable is a cup file, which should handle Syntax Error and throw precise error line and column
4. End to end system by jaxb(trasform AST to XML). The deliverable is e2e system and full test case.
5. presentation(ppt, code )
