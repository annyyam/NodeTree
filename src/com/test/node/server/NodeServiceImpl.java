package com.test.node.server;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.test.node.client.NodeService;
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
    public Node createNode(String ip, Integer parentId, String port) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            Node node = new Node(ip, 0, parentId, port);
            mapper.createNode(node);
            session.commit();
            return node;
        } finally {
            session.close();
        }
    }   
    @Override
    public void updateNode(int id, String newIp, Integer newParentId, String newPort) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            Node node = mapper.getNode(id);
            if (node == null) {
                return;
            }
            node.setIp(newIp);
            node.setParentId(newParentId);
            node.setPort(newPort);
            mapper.updateNode(node);
            session.commit();
        } finally {
            session.close();
        }
    }  
    @Override
    public void deleteNode(int id) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            Node node = mapper.getNode(id);
            if (node == null) {
                return;
            }
            mapper.deleteNode(id);
            session.commit();
        } finally {
            session.close();
        }
    }
}





















