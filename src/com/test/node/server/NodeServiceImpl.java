package com.test.node.server;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.test.node.client.service.NodeService;
import com.test.node.shared.Node;

@SuppressWarnings("serial")
public class NodeServiceImpl extends RemoteServiceServlet implements NodeService {
    @Override
    public List<Node> getAllNodes() {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            List<Node> allNodes = mapper.getAllNodes();
            return allNodes;
        } finally {
            session.close();
        }
    }

    @Override
    public Node getNode(int id) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            return mapper.getNode(id);
        } finally {
            session.close();
        }
    }

    @Override
    public Node saveNode(Node node) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();

        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            if (node.getId() == 0) {
                mapper.createNode(node);
            } else {
                mapper.updateNode(node);
            }
            session.commit();
            return node;
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteNode(Node node) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            if (node == null) {
                return;
            }
            mapper.deleteNode(node);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Node> getChildren(int parentId) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            return mapper.getChildren(parentId);
        } finally {
            session.close();
        }

    }

    @Override
    public List<Node> getRootNodes() {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            return mapper.getRootNodes();
        } finally {
            session.close();
        }
    }
}
