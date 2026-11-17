import { useEffect, useState } from "react";
import api from "./api";
import AuthForm from "./AuthForm";
import PostForm from "./PostForm";

const App = () => {
  const [user, setUser] = useState(localStorage.getItem("username"));
  const [posts, setPosts] = useState([]);

  const loadPosts = async () => {
    const { data } = await api.get("/posts");
    setPosts(data);
  };
  useEffect(() => {
    loadPosts();
    console.log(posts);
  }, []);
  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setUser(null);
  };

  return (
    <div className="min-h-screen bg-slate-50 text-slate-800">
      {/* Header */}
      <header className="bg-white border-b border-slate-200 sticky top-0 z-10 shadow-sm">
        <div className="max-w-4xl mx-auto px-4 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold tracking-tight text-slate-900">
            JWT Blog Demo
          </h1>

          {user && (
            <button
              onClick={logout}
              className="px-4 py-2 text-sm font-medium text-red-600 bg-red-50 hover:bg-red-100 rounded-lg transition-colors cursor-pointer"
            >
              Logout
            </button>
          )}
        </div>
      </header>

      {/* Main Content Area */}
      <main className="max-w-4xl mx-auto px-4 py-8 space-y-8">
        {/* Authentication and Post Form */}
        <section className="bg-white rounded-2xl border border-slate-200 p-6 shadow-sm">
          {user ? (
            <div className="space-y-4">
              <div className="flex items-center justify-between bg-slate-50 px-4 py-3 rounded-xl border border-slate-100">
                <span className="text-sm font-medium text-slate-600">
                  Logged in as{" "}
                  <strong className="text-slate-900 font-semibold">
                    {user}
                  </strong>
                </span>
              </div>

              <PostForm onCreated={loadPosts} />
            </div>
          ) : (
            <AuthForm onLogin={setUser} />
          )}
        </section>

        {/* All Posts Section */}
        <section className="space-y-4">
          <div className="flex items-center justify-between">
            <h2 className="text-lg font-bold text-slate-900">All Posts</h2>

            <span className="text-xs font-semibold uppercase tracking-wider bg-slate-200 text-slate-700 px-2.5 py-1 rounded-full">
              {posts.length} {posts.length === 1 ? "Post" : "Posts"}
            </span>
          </div>

          {posts.length === 0 && (
            <div className="bg-white rounded-2xl border border-dashed border-slate-300 p-12 text-center">
              <p className="text-slate-500 text-sm">
                No posts yet. Be the first to publish one!
              </p>
            </div>
          )}

          <div className="space-y-4">
            {posts.map((p) => (
              <article
                key={p.id}
                className="bg-white rounded-2xl border border-slate-200 p-6 shadow-sm hover:shadow-md transition-shadow space-y-3"
              >
                <h2 className="text-xl font-semibold text-slate-900">
                  {p.title}
                </h2>

                <p className="text-slate-600 leading-relaxed text-sm sm:text-base">
                  {p.content}
                </p>

                <div className="flex flex-wrap items-center justify-between pt-4 border-t border-slate-100 text-xs text-slate-500">
                  <span className="font-medium text-slate-700">
                    By{" "}
                    <span className="text-indigo-600 font-semibold">
                      {p.author}
                    </span>
                  </span>

                  <time dateTime={p.createdAt} className="text-slate-400">
                    {new Date(p.createdAt).toLocaleString()}
                  </time>
                </div>
              </article>
            ))}
          </div>
        </section>
      </main>
    </div>
  );
};
export default App;
