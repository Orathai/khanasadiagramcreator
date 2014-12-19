Khanasa diagram creator via GraphViz Java API
=============================================

**Purpose :** Create KHANASA diagram in png from database schema.

You find the documentation here: \
https://github.com/Orathai/khanasadiagramcreator/wiki/Index

.. image:: src/images/khanasadiagram.png



References
----------

* `http://www.graphviz.org <http://www.graphviz.org>`_
* `GraphViz Java API Loria <http://www.loria.fr/~szathmar/off/projects/java/GraphVizAPI/index.php>`_
* `Spark Java Framework <http://www.sparkjava.com/>`_
* `Docker <https://www.docker.com/>`_
  
Running as docker container
---------------------------

You can run this project without installing java if you use docker::

     docker build -t diagramcreator .
     docker run --rm -p 4567:4567 --name diagramcreator diagramcreator

As you bind the docker application to your local port, you can still use the
instructions from the documentation. Just be aware, that you now will
access your database with the **actual IP address**, and not localhost, as
localhost for the application is where the docker container runs.
  
Author
------

Orathai Khanasa, orathai.khanasa@gmail.com
